# Pet project on Quarkus

This project uses Quarkus to create CRUD API fora user.

Data Base is MySQL DB on Amazon RDS.
Email notifications implemented via Amazon SNS.
Added integration with Amazon S3 to allow user to upload/download files on Amazon S3

In progress:
1. Adding lambda on saving file on S3 to save file's data into DB
2. Deploy the app on EC2