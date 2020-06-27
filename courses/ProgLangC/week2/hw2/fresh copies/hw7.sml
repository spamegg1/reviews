(* University of Washington, Programming Languages, Homework 7, hw7.sml 
   (See also Ruby code.)
*)

(* Do not make changes to this code except where you see comments containing
   the word CHANGE. *)

(* expressions in a little language for 2D geometry objects
   values: points, lines, vertical lines, line segments
   other expressions: intersection of two expressions, lets, variables, 
                      (shifts added by you)
*)
datatype geom_exp = 
           NoPoints
	 | Point of real * real (* represents point (x,y) *)
	 | Line of real * real (* represents line (slope, intercept) *)
	 | VerticalLine of real (* x value *)
	 | LineSegment of real * real * real * real (* x1,y1 to x2,y2 *)
	 | Intersect of geom_exp * geom_exp (* intersection expression *)
	 | Let of string * geom_exp * geom_exp (* let s = e1 in e2 *)
	 | Var of string
(* CHANGE add shifts for expressions of the form Shift(deltaX, deltaY, exp *)

exception BadProgram of string
exception Impossible of string

(* helper functions for comparing real numbers since rounding means
   we should never compare for equality *)

val epsilon = 0.00001

fun real_close (r1,r2) = 
    (Real.abs (r1 - r2)) < epsilon

(* notice curried *)
fun real_close_point (x1,y1) (x2,y2) = 
    real_close(x1,x2) andalso real_close(y1,y2)

(* helper function to return the Line or VerticalLine containing 
   points (x1,y1) and (x2,y2). Actually used only when intersecting 
   line segments, but might be generally useful *)
fun two_points_to_line (x1,y1,x2,y2) = 
    if real_close(x1,x2)
    then VerticalLine x1
    else
	let 
	    val m = (y2 - y1) / (x2 - x1)
	    val b = y1 - m * x1
	in
	    Line(m,b)
	end

(* helper function for interpreter: return value that is the intersection
   of the arguments: 25 cases because there are 5 kinds of values, but
   many cases can be combined, especially because intersection is commutative.
   Do *not* call this function with non-values (e.g., shifts or lets)
 *)
fun intersect (v1,v2) =
    case (v1,v2) of
	
       (NoPoints, _) => NoPoints (* 5 cases *)
     | (_, NoPoints) => NoPoints (* 4 additional cases *)

     | 	(Point p1, Point p2) => if real_close_point p1 p2
				then v1
				else NoPoints

      | (Point (x,y), Line (m,b)) => if real_close(y, m * x + b)
				     then v1
				     else NoPoints

      | (Point (x1,_), VerticalLine x2) => if real_close(x1,x2)
					   then v1
					   else NoPoints

      | (Point _, LineSegment seg) => intersect(v2,v1)

      | (Line _, Point _) => intersect(v2,v1)

      | (Line (m1,b1), Line (m2,b2)) =>
	if real_close(m1,m2) 
	then (if real_close(b1,b2)
	      then v1 (* same line *)
	      else  NoPoints) (* parallel lines do not intersect *)
	else 
	    let (* one-point intersection *)
		val x = (b2 - b1) / (m1 - m2)
		val y = m1 * x + b1
	    in
		Point (x,y)
	    end

      | (Line (m1,b1), VerticalLine x2) => Point(x2, m1 * x2 + b1)

      | (Line _, LineSegment _) => intersect(v2,v1)

      | (VerticalLine _, Point _) => intersect(v2,v1)
      | (VerticalLine _, Line _)  => intersect(v2,v1)

      | (VerticalLine x1, VerticalLine x2) =>
	if real_close(x1,x2)
	then v1 (* same line *)
	else NoPoints (* parallel *)

      | (VerticalLine _, LineSegment seg) => intersect(v2,v1)

      | (LineSegment seg, _) => 
	(* the hard case, actually 4 cases because v2 could be a point,
	   line, vertical line, or line segment *)
	(* First compute the intersection of (1) the line containing the segment 
           and (2) v2. Then use that result to compute what we need. *)
	(case intersect(two_points_to_line seg, v2) of
	    NoPoints => NoPoints 
	  | Point(x0,y0) => (* see if the point is within the segment bounds *)
	    (* assumes v1 was properly preprocessed *)
	    let 
		fun inbetween(v,end1,end2) =
		    (end1 - epsilon <= v andalso v <= end2 + epsilon)
		    orelse (end2 - epsilon <= v andalso v <= end1 + epsilon)
		val (x1,y1,x2,y2) = seg
	    in
		if inbetween(x0,x1,x2) andalso inbetween(y0,y1,y2)
		then Point(x0,y0)
		else NoPoints
	    end
	  | Line _ => v1 (* so segment seg is on line v2 *)
	  | VerticalLine _ => v1 (* so segment seg is on vertical-line v2 *)
	  | LineSegment seg2 => 
	    (* the hard case in the hard case: seg and seg2 are on the same
               line (or vertical line), but they could be (1) disjoint or 
               (2) overlapping or (3) one inside the other or (4) just touching.
	       And we treat vertical segments differently, so there are 4*2 cases.
	     *)
	    let
		val (x1start,y1start,x1end,y1end) = seg
		val (x2start,y2start,x2end,y2end) = seg2
	    in
		if real_close(x1start,x1end)
		then (* the segments are on a vertical line *)
		    (* let segment a start at or below start of segment b *)
		    let 
			val ((aXstart,aYstart,aXend,aYend),
			     (bXstart,bYstart,bXend,bYend)) = if y1start < y2start
							      then (seg,seg2)
							      else (seg2,seg)
		    in
			if real_close(aYend,bYstart)
			then Point (aXend,aYend) (* just touching *)
			else if aYend < bYstart
			then NoPoints (* disjoint *)
			else if aYend > bYend
			then LineSegment(bXstart,bYstart,bXend,bYend) (* b inside a *)
			else LineSegment(bXstart,bYstart,aXend,aYend) (* overlapping *)
		    end
		else (* the segments are on a (non-vertical) line *)
		    (* let segment a start at or to the left of start of segment b *)
		    let 
			val ((aXstart,aYstart,aXend,aYend),
			     (bXstart,bYstart,bXend,bYend)) = if x1start < x2start
							      then (seg,seg2)
							      else (seg2,seg)
		    in
			if real_close(aXend,bXstart)
			then Point (aXend,aYend) (* just touching *)
			else if aXend < bXstart
			then NoPoints (* disjoint *)
			else if aXend > bXend
			then LineSegment(bXstart,bYstart,bXend,bYend) (* b inside a *)
			else LineSegment(bXstart,bYstart,aXend,aYend) (* overlapping *)
		    end	
	    end						
	  | _ => raise Impossible "bad result from intersecting with a line")
      | _ => raise Impossible "bad call to intersect: only for shape values"

(* interpreter for our language: 
   * takes a geometry expression and returns a geometry value
   * for simplicity we have the top-level function take an environment,
     (which should be [] for the whole program
   * we assume the expression e has already been "preprocessed" as described
     in the homework assignment: 
         * line segments are not actually points (endpoints not real close)
         * lines segment have left (or, if vertical, bottom) coordinate first
*)

fun eval_prog (e,env) =
    case e of
	NoPoints => e (* first 5 cases are all values, so no computation *)
      | Point _  => e
      | Line _   => e
      | VerticalLine _ => e
      | LineSegment _  => e
      | Var s => 
	(case List.find (fn (s2,v) => s=s2) env of
	     NONE => raise BadProgram("var not found: " ^ s)
	   | SOME (_,v) => v)
      | Let(s,e1,e2) => eval_prog (e2, ((s, eval_prog(e1,env)) :: env))
      | Intersect(e1,e2) => intersect(eval_prog(e1,env), eval_prog(e2, env))
(* CHANGE: Add a case for Shift expressions *)

(* CHANGE: Add function preprocess_prog of type geom_exp -> geom_exp *)
