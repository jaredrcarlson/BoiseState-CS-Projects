"Account Class - represents a Bank account"

Object subclass: Account [
	|number customer balance|

	"Makes a Deposit to the Account"
	deposit: amount [
		balance:=(balance + amount)
	]
	
	"Withdraws from the Account"
	withdraw: amount [
		balance:=(balance - amount)
	]
	
	"Performs Interest Accrual for the Account"
	"This method must be defined in subclasses"
	accrue: rate [
		^self subclassResponsibility
	]
	
	"Displays Account Information"
	toString [
		|s| s:=(number asString) , ':' , (customer toString) , ':' , (balance asString).
		^s
	]
]
