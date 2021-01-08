(require racket/file)

;; weather-solution.rkt

(define-struct weather (date max-tmp avg-tmp min-tmp rain snow precip))
;; Weather is (make-weather String Number Number Number Number Number Number)
;; interp. Data about weather in Vancouver on some date
;; (make-weather date xt at nt r s p) means that
;; on the day indicated by date:
;; - the maximum temperature in Celsius was xt
;; - the average temperature in Celsius was at
;; - the minimum temperature in Celsius was nt
;; - r millimeters of rain fell
;; - s millimeters of snow fell
;; - p millimeters of total precipitation fell

(define W0 (make-weather "7/2/88" 21.9 17.7 13.4 0.2 0 0.2))

(define (fn-for-weather w)
  (... (weather-date w)
       (weather-max-tmp w)
       (weather-avg-tmp w)
       (weather-min-tmp w)
       (weather-rain w)
       (weather-snow w)
       (weather-precip w)))

(define WEATHER-DATA
  (local [(define (data->weather d)
            (make-weather (first d) (second d) (third d) (fourth d)
                          (fifth d) (sixth d) (seventh d)))]
    (map data->weather (file->value "weather.ss"))))

; PROBLEM:
;
; Complete the design of a function that takes a list of weather data
; and produces the sum total of rainfall in millimeters on days where
; the average temperature was greater than 15 degrees Celsius.
;
; The function that you design must make at least one call to
; built-in abstract functions (there is a very nice solution that
; composes calls to three built-in abstract functions).


;; (listOf Weather) -> Number
;; produce the sum total of rain in a list of weather data on days with > 15 C average temp.

(check-expect (total-warm-rain empty) 0)
(check-expect (total-warm-rain
               (list (make-weather "7/1/88" 16.8 15.3 13.7 2.2 0 2.2)
                     (make-weather "7/2/88" 21.9 17.7 13.4 0.2 0 0.2)
                     (make-weather "7/3/88" 19.5 15.6 11.7 0.4 0 0.4)
                     (make-weather "7/4/88" 18.7 13.7 8.7 0.0 0 0.0)
                     (make-weather "7/5/88" 15.5 13.5 11.4 7.6 0 7.6)))
              (+ 2.2 0.2 0.4))
(check-expect (total-warm-rain WEATHER-DATA) 2545.3)

; <template as calls to foldr, map and filter>

(define (total-warm-rain low)
  (local [(define (warm-day? w)
            (> (weather-avg-tmp w) 15))]
    (foldr + 0
           (map weather-rain
                (filter warm-day? low)))))
