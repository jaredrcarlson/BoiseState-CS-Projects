#!/bin/gprolog --consult-file

% For a given slot Find out who all can meet in that slot.

:- include('data.pl').

% Your code goes here.

available(time(SH,SM),time(SHour,SMinute),time(EH,EM),time(EHour, EMinute)) :- (SHour<SH; SHour=:=SH, SMinute=<SM),
																		       (EHour>EH; EHour=:=EH, EMinute>=EM).

meetone(Person,slot(S,E)) :- free(Person,slot(Start,End)),
							 available(S,Start,E,End).

main :- findall(Person, meetone(Person,slot(time(8,30),time(8,45))), People),
	write(People), nl, halt.

:- initialization(main).
