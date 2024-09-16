assume_role = {
  external_id = "Terraform"
  role_arn    = "arn:aws:iam::149745434630:role/TerraformWriteStateRole"
}
bucket         = "vtx-tfstate-plat-dev-us-east-2"
key            = "vertex-auth-service/dev/vertex-auth-service.tfstate"
region         = "us-east-2"
dynamodb_table = "TerraformLockTable-us-east-2"
encrypt        = true