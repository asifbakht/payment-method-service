variable "sqs_iam_role_name" {
  type        = string
  description = "The role name associated with the SQS queue"
}
variable "app_name" {
  type        = string
  description = "The name of the application"
}
variable "environment" {
  type        = string
  description = "The environment of the application"
}
variable "sqs_queue_arns" {
  type        = list(string)
  description = "The ARN of the SQS queue"
}