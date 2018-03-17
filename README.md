# Event Ticket Sales

### Requirements

~~Anonymous online users can purchase tickets using braintree api.~~

After purchasing online tickets, tickets are dynamically generated as PDFs and emailed to the purchaser.  

Application also emails two reports: weekly cumulative profit (total accumulated revenue since launch) and total event sales (cumulative sales of each event)

### Roadmap

1. administration section
    1. ~~admin inputs, edits, deletes event and ticket information~~
    2. admin logs on with credentials
    3. ~~configuration is read from file~~
	
2. customer can use landing page to purchase a ticket
	1. ~~customer selects event~~
	2. ~~customer selects tickets~~
	3. ~~customer confirms purchase~~
	
3. purchase is processed
	1. ~~customer enters payment information (name, email, cc, etc.)~~
	2. ~~braintree api processes payment~~
	3. payment is split between multiple accounts
		3.3.1 if payment can't be split by braintree, send a payment to a third party using paypal or something similar
	4. sales information, including customer information, is recorded in database
    5. ticket quantities decrements by 1
    6. customer views receipt information and link to download ticket
    
4. tickets are emailed to user
	1. apache fop creates dynamic tickets
	2. ticket contains ticket logo, ticket background, QR code, customer name, and sales description
	3. tickets are emailed to customer

5. web application generates reports
	1. generates total sales
	2. primary sales report
	3. split sales report (periodic or on-demand)
	4. produces a event ticket list report that contains names, numbers, email, and tickets purchased
	
6. admin dashboard
	1. dashboard contains simple graphs of profit (line charts)
	2. link to google analytics

#### Challenges
* surcharge goes to primary person
* generated ticket looks similar to fandango
* change ticket background to advertisement
* recaptcha on login
* interceptor requires session.loggedIn to be true for admin pages
* ~~braintree and ticket, Confirmatiom view~~
* comp tickets (you select a ticket and enter an name, email address, phone)
* ~~ticket policy modal confirmation before charging credit card~~
* ~~validation on confirmation~~
* ~~flash error messages on briantree failures~~
* receipt landing page on success
* write sale data to domain
* autotimestamp all domains
* itemmap to rawrecord.  raw record to itemmap in ticketservice