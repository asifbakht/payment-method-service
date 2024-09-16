data "aws_secretsmanager_secret" "app_secrets" {
  name = "eks/${var.environment}/${var.app_name}/app"
}

data "aws_secretsmanager_secret" "database_secrets" {
  name = "eks/${var.environment}/${var.app_name}/database"
}

data "aws_secretsmanager_secret" "auth0_secrets" {
  name = "eks/${var.environment}/${var.app_name}/auth0"
}

data "aws_secretsmanager_secret_version" "app_secrets_version" {
  secret_id = data.aws_secretsmanager_secret.app_secrets.id
}

data "aws_secretsmanager_secret_version" "database_secrets_version" {
  secret_id = data.aws_secretsmanager_secret.database_secrets.id
}

data "aws_secretsmanager_secret_version" "auth0_secrets_version" {
  secret_id = data.aws_secretsmanager_secret.auth0_secrets.id
}

data "aws_secretsmanager_secret" "vas_secrets" {
  name = "eks/${var.secret_env_prefix}/${var.app_name}/${var.app_name}/secrets"
}

locals {
  app_secrets = jsondecode(
    replace(
      replace(
        data.aws_secretsmanager_secret_version.app_secrets_version.secret_string,
        "clientSecret", "app_clientSecret"
      ),
    "uuidNamespace", "app_uuidNamespace")
  )
  database_secrets = jsondecode(
    replace(
      replace(
        data.aws_secretsmanager_secret_version.database_secrets_version.secret_string,
        "vtxauthUsername", "db_username"
      ),
    "vtxauthPassword", "db_password")
  )
  auth0_secrets = jsondecode(
    replace(
      replace(
        data.aws_secretsmanager_secret_version.auth0_secrets_version.secret_string,
        "clientId", "auth0_clientId"
      ),
  "clientSecret", "auth0_clientSecret"))
}
resource "aws_secretsmanager_secret_version" "vas_secret_version" {
  secret_id     = data.aws_secretsmanager_secret.vas_secrets.id
  secret_string = jsonencode(merge(local.app_secrets, local.database_secrets, local.auth0_secrets))
}

