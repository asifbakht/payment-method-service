resource "aws_sqs_queue" "my_queue" {
  name = var.sqs_queue_name
}
