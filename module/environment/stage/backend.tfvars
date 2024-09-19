# assume_role = {
#   external_id = "Terraform"
#   role_arn    = "arn:aws:iam::564154588723:role/TerraformWriteStateRole"
# }
bucket         = "vtx-tfstate-plat-stage-us-east-1"
key            = "payment-auth-service/stage/payment-auth-service.tfstate"
region         = "us-east-1"
# dynamodb_table = "TerraformLockTable-us-east-1"
encrypt        = true
