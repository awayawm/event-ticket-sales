# Event Ticket Sales

### Requirements

~~Anonymous online users can purchase tickets using braintree api.~~

~~After purchasing online tickets, tickets are dynamically generated as PDFs and emailed to the purchaser.~~

Application generates various reports

### Roadmap

1. administration section
    1. ~~admin inputs, edits, deletes event and ticket information~~
    2. admin logs on with credentials
    3. ~~configuration is read from file~~
    4. recaptcha on login page
    5. dashboard contains simple graphs of profit (line charts)
    6. link to google analytics
	
2. customer can use landing page to purchase a ticket
	1. ~~customer selects event~~
	2. ~~customer selects tickets~~
	3. ~~customer confirms purchase~~
	
3. purchase is processed
	1. ~~customer enters payment information (name, email, cc, etc.)~~
	2. ~~braintree api processes payment~~
	3. ~~payment is split between multiple accounts~~
		~~3.3.1 if payment can't be split by braintree, send a payment to a third party using paypal or something similar~~
	4. ~~sales information, including customer information, is recorded in database~~
    5. ~~ticket quantities decrements by 1~~
    6. ~~customer views receipt information and link to download ticket~~
    
4. tickets are emailed to user
	1. ~~apache fop creates dynamic tickets~~
	2. ~~ticket contains ticket logo, ticket background, QR code, customer name, and sales description~~
	3. ~~tickets are emailed to customer~~

5. web application generates reports
	1. generates total sales (primary, split, total), sent periodically
	2. generates event sales (primary, split, total), sent periodically
	3. generate event ticket list that contains name, email, phone, raw records in a list
	4. generate credit card transactions report, sent periodically

#### Sprint
* recaptcha on login
* comp tickets (you select a ticket and enter an name, email address, phone)
* periodically check cc status and update sales
* fix qr code or figure out how to reliably scan it
* interceptor requires session.loggedIn to be true for admin pages
* login page
* remove seconds from all visible datetimes
* dynamic titles from config
* additional sales sample data
* deploy to elastic bean stalk
* migrate production datasource to rdb
* add logging