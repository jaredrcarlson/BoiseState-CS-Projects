"SavingsAccount Class - represents a Bank savings account"

Account subclass: SavingsAccount [
	|interest|
	
	"Constructor"
	SavingsAccount class [
		new [
			^super new init
		]
		
		"New with Parameters"
		new: sa_number Customer: sa_customer number: sa_balance [
			^self new init: sa_number Customer: sa_customer number: sa_balance
		]
	]
	
	"Initializer"
	init [
		interest:=0
	]
	
	"Initializer with Parameters"
	init: sa_number Customer: sa_customer number: sa_balance [
		number:=sa_number.
		customer:=sa_customer.
		balance:=sa_balance.
		interest:=0
	]
	
	"(Override super) Performs Interest Accrual for the Account"
	accrue: rate [
		balance:= balance + (balance * rate).
		interest:= interest + (balance * rate)
	]
	
]