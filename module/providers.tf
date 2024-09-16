terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.67.0"
    }
  }
  backend "s3" {}
}
provider "aws" {
  region = var.primary_region
  # assume_role {
  #   role_arn = "arn:aws:iam::${var.aws_account_id}:role/TerraformProvisioningRole"
  # }
  default_tags {
    tags = {
      Owner               = "asifbakht@gmamil.com"
      Product             = "Payment Auth Service"
      Environment         = "${var.environment}-primary"
      Data_Classification = "Confidential"
      Created_Using       = "Terraform"
      Source_Code         = "${var.app_name}-deployment"
    }
  }
}
provider "aws" {
  region = var.secondary_region
  alias  = "secondary_region"
  # assume_role {
  #   role_arn = "arn:aws:iam::${var.aws_account_id}:role/TerraformProvisioningRole"
  # }
  default_tags {
    tags = {
      Owner               = "asifbakht@gmail.com"
      Product             = "Payment Auth Service"
      Environment         = "${var.environment}-secondary"
      Data_Classification = "Confidential"
      Created_Using       = "Terraform"
      Source_Code         = "${var.app_name}-deployment"
    }
  }
}
