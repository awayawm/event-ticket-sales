# Event Ticket Sales

### Requirements

Anonymous online users can purchase tickets using braintree api.  

After purchasing online tickets, tickets are dynamically generated as PDFs and emailed to the purchaser.  

Application also emails two reports: weekly cumulative profit (total accumulated revenue since launch) and total event sales (cumulative sales of each event)

### Roadmap

1. administration section
    1. ~~admin inputs, edits, deletes event and ticket information~~
    2. admin logs on with credentials
    3. ~~configuration is read from file~~
    4. config index/controller
    6. help index/controller
	
2. customer can use landing page to purchase a ticket
	1. ~~customer selects event~~
	2. ~~customer selects tickets~~
	3. customer confirms purchase
	
3. purchase is processed
	1. customer enters payment information (name, email, cc, etc.)
	2. braintree api processes payment
	3. payment is split between multiple accounts
		3.3.1 if payment can't be split by braintree, send a payment to a third party using paypal or something similar
	4. sales information, including customer information, is recorded in database
    5. ticket quantity decrements by 1
    
4. tickets are emailed to user
	1. apache fop creates dynamic tickets
	2. ticket contains ticket logo, ticket background, QR code, customer name, and sales description
	3. tickets are emailed to customer

5. web application generates reports
	1. generates cumulate sales report, emailed periodically (periodically or on-demand)
	2. generates total sales per event report (periodically or on-demand)
	
6. admin dashboard
	1. dashboard contains simple graphs of profit (line charts)
	2. link to google analytics

#### Challenges
* surcharge goes to primary person
* ticket images are logo and advertisment.  create a ticket similar to fandango
* recaptcha on login
	
#### Technical Debt
* fix date year

#### Current Sprint
* ~~icons on functions~~
* ~~confirmation on delete~~
* ~~edit tickets~~
* ~~insert sample data automatically when running in development~~
* ~~create event~~
* ~~index events~~
* ~~edit events~~
* ~~create sample config in project~~
* ~~update admin urls to /admin/ticket/create etc.~~
* ~~load config from file if env var is present otherwise load sample data in project~~
* interceptor requires session.loggedIn to be true for certain pages
* braintree and ticket, Confirmatiom view
* comp tickets (you select a ticket and enter an name, email address, phone)
