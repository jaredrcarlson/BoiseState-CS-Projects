"Customer Class - represents a Bank customer"

Object subclass: Customer [
	|name|
	
	"Constructor"
	Customer class [
		new [
			^super new
		]
		
		"New with Parameters"
		new: custname [
			^self new init: custname
		]
	]
	
	"Initializer"
	init: custname [
		name:=custname
	]
		
	"Gets customer name"
	toString [
		^(name asString)
	]
]

