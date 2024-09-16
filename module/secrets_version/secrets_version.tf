
resource "aws_secretsmanager_secret_version" "vas_secret_version" {
  secret_id     = data.aws_secretsmanager_secret.vas_secrets.id
  secret_string = jsonencode("{'asif':'bakht'}")
}

