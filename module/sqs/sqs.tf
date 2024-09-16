resource "aws_sqs_queue" "vertex_auth_service_queue" {
  name = var.sqs_queue_name
}