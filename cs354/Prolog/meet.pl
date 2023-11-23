#!/bin/gprolog --consult-file

% For a group of people find out when they can meet.

:- include('data.pl').
:- include('uniq.pl').

next(N,[F|_],S,E) :- N = F, match(N,S,E).

match(Name,(SH,SM),(EH,EM)) :- free(Name,slot(time(SH,SM),time(EH,EM))).	

newList(L,[_|T]) :- L = T.

assign((SourceS,SourceM),(TargetS,TargetM)) :- TargetS is SourceS, TargetM is SourceM.

lt((H1,M1),(H2,M2)) :- H1 @< H2; H1 == H2, M1 @< M2.
equal((H1,M1),(H2,M2)) :- H1 == H2, M1 == M2.

endTime(ET,T1,T2) :- lt(T1,T2), assign(T1,ET); lt(T2,T1), assign(T2,ET); T1 == T2, assign(T1,ET).
startTime(LT,T1,T2) :- lt(T1,T2), assign(T2,LT); lt(T2,T1), assign(T1,LT); T1 == T2, assign(T1,LT).

earlier(T1,T2) :- lt(T1,T2).
later(T1,T2) :- lt(T2,T1).
same(T1,T2) :- equal(T1,T2).

earlierOrSame(T1,T2) :- earlier(T1,T2); same(T1,T2).
laterOrSame(T1,T2) :- later(T1,T2); same(T1,T2).

overlap(_,S1,E1,_,S2,E2,Start,End) :-	earlierOrSame(S1,S2), later(E1,S2);
										later(S1,S2), later(E2,S1).
																	
whileMore(_,[],NMS,NME,MS,ME) :- assign(NMS,MS), assign(NME,ME).

whileMore(G,Others,PS,PE,MS,ME) :-   	Others == [];
										next(O,Others,OS,OE),
										overlap(G,PS,PE,O,OS,OE,NMS,NME),
										startTime(NMS,PS,OS),
										endTime(NME,PE,OE),
										newList(Rest,Others),
										whileMore(G,Rest,NMS,NME,MS,ME).
											
meet((start(Start),end(End))) :- 	people(Group),
									next(P,Group,PS,PE),
									newList(Others,Group),
									whileMore(P,Others,PS,PE,Start,End).
								
people([ann,bob,carla]).

main :-	findall(Slot, meet(Slot), Slots),
        uniq(Slots, Uniq),
        write(Uniq), nl, halt.

:- initialization(main).
