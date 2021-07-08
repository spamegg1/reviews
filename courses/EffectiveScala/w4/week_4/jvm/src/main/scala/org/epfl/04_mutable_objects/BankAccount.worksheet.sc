class BankAccount:
  private var balance: Int = 0
  def deposit(amount: Int): Int = 
    if amount > 0 then balance = balance + amount
    balance
  
  def withdraw(amount: Int): Int = 
    if amount > 0 && amount <= balance then
      balance = balance - amount
    balance
end BankAccount

val account = BankAccount()

account.deposit(30)
account.deposit(30)
account.withdraw(50)