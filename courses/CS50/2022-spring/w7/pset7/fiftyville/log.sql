-- Keep a log of any SQL queries you execute as you solve the mystery.
-- Use: cat log.sql | sqlite3 fiftyville.db > output.txt

-- Check reports for when and where the crime happened.
SELECT *
  FROM crime_scene_reports
 WHERE street = "Humphrey Street"
   AND year = 2021
   AND month = 7
   AND day = 28;

-- Theft happened at 10:15 a.m.
-- Above report mentions three witness interview transcripts mentioning bakery.
SELECT *
  FROM interviews
 WHERE year = 2021
   AND month = 7
   AND day = 28
   AND transcript LIKE "%bakery%";

-- Ruth says within ten minutes of theft, thief drove from bakery parking lot.
-- ** LEAD 1 ** Check bakery security camera footage of that time.
-- Eugene says thief withdrew money from Leggett Street ATM earlier in morning.
-- ** LEAD 2 ** Check ATM transactions that morning.
-- Raymond says thief talked to someone on the phone less than 1 minute, told to
-- buy a plane ticket for earliest flight out of Fiftyville the next day.
-- ** LEAD 3 ** Check flights the next day.
-- ** LEAD 4 ** Check phone calls that are less than 1 minute (60 seconds).
-- Emma says someone talked on the phone for half an hour.
-- This is not relevant to the case.

-- ** LEAD 1 ** check bakery security footage, 2021-7-28 10:15-10:25 a.m.
SELECT *
  FROM bakery_security_logs
 WHERE year = 2021
   AND month = 7
   AND day = 28
   AND hour = 10
   AND minute >= 15
   AND minute <= 25;

-- This gives us 8 possible license plates.
-- ** LEAD 5 ** Check license plates.

-- ** LEAD 2 ** check ATM transactions on Leggett Street, 2021-7-28.
SELECT *
  FROM atm_transactions
 WHERE year = 2021
   AND month = 7
   AND day = 28
   AND atm_location = "Leggett Street"
   AND transaction_type = "withdraw";

-- This gives us 8 possible account numbers.
-- ** LEAD 6 ** Check bank account numbers.

-- ** LEAD 3 ** check earliest flight out of Fiftyville on 2021-7-29.
SELECT *
  FROM flights
  JOIN airports
    ON airports.id = flights.origin_airport_id
 WHERE airports.city = "Fiftyville"
   AND flights.year = 2021
   AND flights.month = 7
   AND flights.day = 29
 ORDER BY flights.hour
 LIMIT 1;

-- The thief escaped to city with destination_airport_id = 4.
SELECT city
  FROM airports
 WHERE id = 4;

-- That's New York City. SECOND QUESTION ANSWERED!
-- ** LEAD 7 ** check passengers for that flight.

-- ** LEAD 4 ** Check phone records 2021-7-28, for less than 60 seconds call.
SELECT *
  FROM phone_calls
 WHERE year = 2021
   AND month = 7
   AND day = 28
   AND duration <= 60;

-- This gives 10 phone calls.
-- ** LEAD 8 ** check the phone numbers.

-- ** LEAD 5** The 8 license plates can be used on the people table.
-- ** LEAD 6** The 8 bank accounts have a person_id field referencing people.id
-- ** LEAD 7** Passengers have passport_number which can be used on people table
-- ** LEAD 8** The phone numbers can be used on the people table.

-- ** LEAD 5 ** One of these 8 people is the thief.
SELECT *
  FROM people
 WHERE license_plate = "5P2BI95" OR license_plate = "94KL13X"
    OR license_plate = "6P58WS2" OR license_plate = "4328GD8"
    OR license_plate = "G412CB7" OR license_plate = "L93JTIZ"
    OR license_plate = "322W7JE" OR license_plate = "0NTHK55";

-- ** LEAD 6 ** One of these 8 people is the thief.
SELECT *
  FROM people
  JOIN bank_accounts
    ON bank_accounts.person_id = people.id
 WHERE bank_accounts.account_number = 28500762
    OR bank_accounts.account_number = 28296815
    OR bank_accounts.account_number = 76054385
    OR bank_accounts.account_number = 49610011
    OR bank_accounts.account_number = 16153065
    OR bank_accounts.account_number = 25506511
    OR bank_accounts.account_number = 81061156
    OR bank_accounts.account_number = 26013199;

-- ** LEAD 7 ** One of these 8 people is the thief.
SELECT *
  FROM passengers
  JOIN people
    ON passengers.passport_number = people.passport_number
 WHERE passengers.flight_id = 36;

-- ** LEAD 8 (callers) ** One of these is the thief.
SELECT *
  FROM people
 WHERE phone_number = "(130) 555-0289" OR phone_number = "(499) 555-9472"
    OR phone_number = "(367) 555-5533" OR phone_number = "(609) 555-5876"
    OR phone_number = "(499) 555-9472" OR phone_number = "(286) 555-6063"
    OR phone_number = "(770) 555-1861" OR phone_number = "(031) 555-6622"
    OR phone_number = "(826) 555-1652" OR phone_number = "(338) 555-6650";

-- ** LEAD 8 (receivers) ** One of these is the accomplice.
SELECT *
  FROM people
 WHERE phone_number = "(996) 555-8899" OR phone_number = "(892) 555-8872"
    OR phone_number = "(375) 555-8161" OR phone_number = "(389) 555-5198"
    OR phone_number = "(717) 555-1342" OR phone_number = "(676) 555-6554"
    OR phone_number = "(725) 555-3243" OR phone_number = "(910) 555-3251"
    OR phone_number = "(066) 555-9701" OR phone_number = "(704) 555-2131";

-- Looks like Bruce is the thief, and he called Robin on the phone.
