variable "secret_env_prefix" {
    type = string 
    description = "The prefix used in new secret. dev, qa, stage, prod"
}
variable "app_name" {
    type        = string
    description = "The application name"
}
variable "environment" {
    type        = string
    description = "The environment name"
}