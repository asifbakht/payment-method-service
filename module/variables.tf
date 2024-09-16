variable "app_name" {
  type        = string
  description = "The application name"
}
variable "environment" {
  type        = string
  description = "The environment name"
}
variable "primary_region" {
  type        = string
  description = "The primary regio where the resources will be provisioned"
}
variable "secondary_region" {
  type        = string
  description = "The secondary region provisions based on environment"
}
variable "secret_env_prefix" {
  type        = string
  description = "The prefix used in secret name when provisioning. e.g dev, qa, stg, prod"
}
variable "aws_account_id" {
  type        = string
  description = "The aws account id to provision resources"
}
variable "support_secondary" {
  type        = bool
  description = "Indicates if the resources should be provisioned to a secondary region"
  default     = false
}
# variable "replica_region" {
#   type = list(object({
#     region = string
#   }))
#   description = "The list of regions to replicate secrets"
#   default = []
# }

