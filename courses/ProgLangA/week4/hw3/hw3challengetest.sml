use "hw3challenge.sml";

type triple = string * string * typ

(* test input data for typecheck_patterns *)
val inp01: triple list * pattern list = (nil, nil)
val inp02: triple list * pattern list = ([("a", "a", Anything)], nil)
val inp03: triple list * pattern list = (nil, [Wildcard])
val inp04: triple list * pattern list = (nil, [ConstP 10, Variable "a"])
val inp05: triple list * pattern list = (
    nil,
    [TupleP[Variable "a", ConstP 10, Wildcard],
     TupleP[Variable "b", Wildcard, ConstP 11],
     Wildcard]
)
val inp06: triple list * pattern list = (
    nil,
    [TupleP[Wildcard, Wildcard],
     TupleP[Wildcard, TupleP[Wildcard, Wildcard]]]
)
val inp07: triple list * pattern list = (
    nil,
    [TupleP[TupleP[TupleP[Wildcard]], TupleP[TupleP[Wildcard], Wildcard]],
     TupleP[TupleP[    Wildcard    ], TupleP[Wildcard, TupleP[Wildcard]]]]
)
(* if Constructor is given wrong argument, we return NONE *)
val inp08a: triple list * pattern list = (
    [("Empty", "list", UnitT),
     ("List", "list", TupleT[Anything, Datatype "list"])],
    [ConstructorP("empty", UnitP),                (* wrong! should be "Empty" *)
     ConstructorP("List", TupleP[ConstP 10, ConstructorP("Empty", UnitP)]),
     Wildcard]
)
(* here UnitT does not match ConstP 10 so we return NONE. *)
val inp08b: triple list * pattern list = (
    [("Empty", "list", UnitT),
     ("List", "list", TupleT[UnitT, Datatype "list"])],
    [ConstructorP("Empty", UnitP),
     ConstructorP("List", TupleP[ConstP 10, ConstructorP("Empty", UnitP)]),
     Wildcard]
)
val inp09a: triple list * pattern list = (
    [("Red", "color", UnitT),
     ("Green", "color", UnitT),
     ("Blue", "color", UnitT)],
    [ConstructorP("Red", UnitP), Wildcard]
)
val inp09b: triple list * pattern list = (
    [("Red", "color", UnitT),
     ("Green", "color", UnitT),
     ("Blue", "color", UnitT)],
    [ConstructorP("Red", Variable "x")]
)
val inp09c: triple list * pattern list = (
    [("Red", "color", UnitT),
     ("Green", "color", UnitT),
     ("Blue", "color", UnitT)],
    [ConstructorP("Red", Wildcard)]
)
val inp09d: triple list * pattern list = (
    [("Red", "color", UnitT),
     ("Green", "color", UnitT),
     ("Blue", "color", UnitT)],
    [ConstructorP("Red", ConstP 10)]
)
val inp10a: triple list * pattern list = (
    [("Sedan", "auto", Datatype "color"),
     ("Truck", "auto", TupleT[IntT, Datatype "color"]),
     ("SUV", "auto", UnitT)],
    [ConstructorP("Sedan", Variable "a"),
     ConstructorP("Truck", TupleP[Variable "b", Wildcard]),
     Wildcard]
)
(* Here IntT does not match Datatype "color" so we return NONE *)
val inp10b: triple list * pattern list = (
    [("Sedan", "auto", Datatype "color"),
     ("Truck", "auto", TupleT[IntT, Datatype "color"]),
     ("SUV", "auto", UnitT)],
    [ConstructorP("Sedan", ConstP 10),
     ConstructorP("Truck", TupleP[Variable "b", Wildcard]),
     Wildcard]
)
val inp10c: triple list * pattern list = (
    [("Sedan", "auto", Datatype "color"),
     ("Truck", "auto", TupleT[IntT, Datatype "color"]),
     ("SUV", "auto", UnitT)],
    [ConstructorP("Sedan", Variable "a"),
     ConstructorP("Truck", TupleP[Variable "b", ConstP 10]),   (* same as 10b *)
     Wildcard]
)
val inp10d: triple list * pattern list = (
    [("Sedan", "auto", Datatype "color"),
     ("Truck", "auto", TupleT[IntT, Datatype "color"]),
     ("SUV", "auto", UnitT)],
    [ConstructorP("Sedan", Variable "a"),
     ConstructorP("Truck", TupleP[ConstP 10, UnitP]), (* UnitT not Datatype "color" *)
     Wildcard]
)
val inp11: triple list * pattern list = (
    [("Empty", "list", UnitT),
     ("List", "list", TupleT[Anything, Datatype "list"])],
    [ConstructorP("Empty", UnitP),
     ConstructorP("List", TupleP[ConstP 10, ConstructorP("Empty", UnitP)]),
     Wildcard]
)
val inp12: triple list * pattern list = (
    [("Empty", "list", UnitT),
     ("List", "list", TupleT[Anything, Datatype "list"])],
    [ConstructorP("Empty", UnitP),
     ConstructorP("List", TupleP[Variable "k", Wildcard])]
)
val inp13: triple list * pattern list = (
    [("Empty", "list", UnitT),
     ("List", "list", TupleT[Anything, Datatype "list"])],
    [ConstructorP("Empty", UnitP),
     ConstructorP("List",
                  TupleP[ConstructorP("Sedan", Variable "c"), Wildcard])]
)
val inp14: triple list * pattern list = (
    [("Empty", "list", UnitT),
     ("List", "list", TupleT[Anything, Datatype "list"])],
    [ConstructorP("Empty", UnitP),
     ConstructorP("List", TupleP[ConstructorP("Sedan", Variable "c")])]
)
val inp15: triple list * pattern list = (
    nil,
    [TupleP[Variable "x", Variable "y"], TupleP[Wildcard, Wildcard]]
)

(* test results for typecheck_patterns *)
(* val test01: bool = typecheck_patterns inp01 = NONE
val test02: bool = typecheck_patterns inp02 = NONE *)
val test03: bool = typecheck_patterns inp03 = SOME(Anything)
val test04: bool = typecheck_patterns inp04 = SOME(IntT)
val test05: bool = typecheck_patterns inp05 = SOME(TupleT[Anything,IntT,IntT])
val test06: bool = typecheck_patterns inp06 =
    SOME(TupleT[Anything, TupleT[Anything, Anything]])
val test07: bool = typecheck_patterns inp07 =
    SOME(TupleT[
            TupleT[TupleT[Anything]],
            TupleT[TupleT[Anything], TupleT[Anything]]])
val test08a: bool = typecheck_patterns inp08a = NONE
val test08b: bool = typecheck_patterns inp08b = NONE
val test09a: bool = typecheck_patterns inp09a = SOME(Datatype "color")
val test09b: bool = typecheck_patterns inp09b = SOME(Datatype "color")
val test09c: bool = typecheck_patterns inp09c = SOME(Datatype "color")
val test09d: bool = typecheck_patterns inp09d = NONE
val test10a: bool = typecheck_patterns inp10a = SOME(Datatype "auto")
val test10b: bool = typecheck_patterns inp10b = NONE
val test10c: bool = typecheck_patterns inp10c = NONE
val test10d: bool = typecheck_patterns inp10d = NONE
val test11: bool = typecheck_patterns inp11 = SOME(Datatype "list")
val test12: bool = typecheck_patterns inp12 = SOME(Datatype "list")
val test13: bool = typecheck_patterns inp13 = NONE
val test14: bool = typecheck_patterns inp14 = NONE
val test15: bool = typecheck_patterns inp15 = SOME(TupleT[Anything, Anything])
