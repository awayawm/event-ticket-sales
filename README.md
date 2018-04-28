# Event Ticket Sales

### Requirements

~~Anonymous online users can purchase tickets using braintree api.~~

~~After purchasing online tickets, tickets are dynamically generated as PDFs and emailed to the purchaser.~~

Application generates various reports

### Roadmap

1. administration section
    1. ~~admin inputs, edits, deletes event and ticket information~~
    2. ~~admin logs on with credentials~~
    3. ~~configuration is read from file~~
    4. recaptcha on login page
    5. dashboard contains simple graphs of profit (total sales, revenue)
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
	1. generates total sales (select date range -> start date, end date) (primary, secondary, combined)
	2. generates event sales (select event)(primary, secondary, combined), sent at close of event or on demand
	3. generate event ticket list that contains name, email, phone, raw records in a list
	4. generate credit card transactions report, sent periodically.  30 day cc status and 

#### Sprint
* comp tickets (you select a ticket and enter an name, email address, phone)
* fix qr code or figure out how to reliably scan it
* remove seconds from all visible datetimes
* dynamic titles from config
* sales sample data, bootstrap sample data from webapp
* quart task to disable events after eventstop date - send event list, event sales
* text message on settled payment
* void transaction, refund transaction, etc.
* detail sales information on click
* validate file type on upload (disallow gifs, allow jpgs, png, supported fop types) - https://stackoverflow.com/questions/4328947/limit-file-format-when-using-input-type-file?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
* additional cc validation from braintree - https://articles.braintreepayments.com/guides/fraud-tools/basic/overview
* instead of hiding sold out tickets, disable option/select and make card background gray
* forward to https or generate error when http