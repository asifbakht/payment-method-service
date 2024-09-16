variable "secret_name" {
  type        = string
  description = "The name of secret when provisioning"
}

variable "description" {
  type        = string
  description = "The description of the secret"
}

variable "replica_region" {
  type        = list(string)
  description = "the list of regions to replicate secrets"
  default     = []
}
