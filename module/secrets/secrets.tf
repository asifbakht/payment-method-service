resource "aws_secretsmanager_secret" "secret" {
  name        = var.secret_name
  description = var.description

  dynamic "replica" {
    for_each = var.replica_region
    content {
      region = replica.value.region
    }
  }
}