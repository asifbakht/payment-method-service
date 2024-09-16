resource "aws_iam_policy" "payment_auth_service_sqs_policy" {
  name        = "${var.app_name}-sqs-${var.environment}-policy"
  description = "Permissions for ${var.app_name} to access SQS"
  policy = jsonencode({
    "Version" : "2012-10-17",
    "Statement" : [
      {
        Effect : "Allow",
        Action : [
          "sqs:SendMessage"
        ],
        Resource : var.sqs_queue_arns
      }
    ]
  })
}
resource "aws_iam_policy_attachment" "payment_auth_service_sqs_policy" {
  name       = "${var.app_name}-sqs-${var.environment}-attachment"
  roles      = [var.sqs_iam_role_name]
  policy_arn = aws_iam_policy.payment_auth_service_sqs_policy.arn
}
