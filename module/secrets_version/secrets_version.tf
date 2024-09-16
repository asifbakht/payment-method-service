
resource "aws_secretsmanager_secret_version" "vas_secret_version" {
  secret_id     = "eks/${var.secret_env_prefix}/${var.app_name}/${var.app_name}/secrets"
  secret_string = jsonencode("{'asif':'bakht'}")
}

