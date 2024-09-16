output "sqs_queue_arn" {
  value       = aws_sqs_queue.payment_auth_service_queue.arn
  description = "The ARN of the VAS SQS queue"
}
