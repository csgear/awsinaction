
## How do I associate a Route 53 private hosted zone with a VPC in a different AWS account or Region?

- create a zone in production account
VKE-PRODUCTION
ZONEID: Z07267353OBGELZZOURV7

- find the vpc id in development account

VKE-DEVELOPMENT
VPCID: vpc-0bf442a8489c6127e

- associate host-zone hosted in vke-production to vke-development
aws route53 create-vpc-association-authorization --hosted-zone-id Z07267353OBGELZZOURV7 --vpc VPCRegion=us-east-1,VPCId=vpc-0bf442a8489c6127e --region us-east-1 --profile vke-production

aws route53 associate-vpc-with-hosted-zone --hosted-zone-id Z07267353OBGELZZOURV7 --vpc VPCRegion=us-east-1,VPCId=vpc-0bf442a8489c6127e --region us-east-1 --profile vke-development

aws route53 delete-vpc-association-authorization --hosted-zone-id Z07267353OBGELZZOURV7  --vpc VPCRegion=us-east-1,VPCId=vpc-0bf442a8489c6127e --region us-east-1 --profile vke-production

