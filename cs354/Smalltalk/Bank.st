"Bank Class - represents a financial Bank"

Object subclass: Bank [
	|accounts|
	
	"Constructor"
	Bank class [
		new [
			^super new init
		]
	]
	
	"Initializer"
	init [
		accounts:= OrderedCollection new
	]
	
	"Adds a new Account to the Bank"
	add: account [
		accounts add: account
	]
	
	"Performs Interest Accrual for all account in the Bank"
	accrue: rate [
		accounts do: [ :account | account accrue: rate ]
	]

	"Displays Account Information for all accounts in the Bank"
	toString [
		|s| s:=String new.
		accounts do: [ :account | s:=s , (account toString) , (Character nl asString) ].
		^s
	]

]

|bank| bank:=Bank new.
|c| c:=Customer new: 'Ann'.
bank add: (CheckingAccount new: '01001' Customer: c number: 100.00).
bank add: (SavingsAccount new: '01002' Customer: c number: 200.00).
bank accrue: 0.02.
(bank toString) displayNl
