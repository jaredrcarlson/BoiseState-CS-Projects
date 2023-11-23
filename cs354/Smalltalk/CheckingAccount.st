"CheckingAccount Class - represents a Bank checking account"

Account subclass: CheckingAccount [

	"Constructor"
	CheckingAccount class [
		new [
			^super new
		]
		
		"New with Parameters"
		new: ca_number Customer: ca_customer number: ca_balance [
			^self new init: ca_number Customer: ca_customer number: ca_balance
		]
	]
	
	"Initializer"
	init: ca_number Customer: ca_customer number: ca_balance [
		number:=ca_number.
		customer:=ca_customer.
		balance:=ca_balance
	]
	
	"Accrue - N/A"
	accrue: rate []
	

]
