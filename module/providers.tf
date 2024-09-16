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
  region = var.main_region
  assume_role {
    role_arn = "arn:aws:iam::${var.aws_account_id}:role/TerraformProvisioningRole"
  }
  default_tags {
    tags = {
      Owner               = "CFSFawkes@vertexinc.com"
      Product             = "Vertex Auth Service"
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
  assume_role {
    role_arn = "arn:aws:iam::${var.aws_account_id}:role/TerraformProvisioningRole"
  }
  default_tags {
    tags = {
      Owner               = "CFSFawkes@vertexinc.com"
      Product             = "Vertex Auth Service"
      Environment         = "${var.environment}-secondary"
      Data_Classification = "Confidential"
      Created_Using       = "Terraform"
      Source_Code         = "${var.app_name}-deployment"
    }
  }
}
