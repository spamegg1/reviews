## I don’t feel like doing this one! Sorry!

# [Duo Mobile](https://cs50.harvard.edu/college/2022/spring/test/duo/#duo-mobile)

![XKCD comic](https://cs50.harvard.edu/college/2022/spring/test/duo/two_factor_security_key_2x.png)

Source: [xkcd.com/2522](https://xkcd.com/2522/)

Logging into an app or website using (only) a username and password  is considered “single-factor authentication” (SFA), whereby the password is a “knowledge factor” that (ideally) only you know. More secure than  SFA is two-factor authentication (2FA), otherwise known (less precisely) as two-step authentication, whereby a second factor is required in  order to log in. For instance, both Harvard and Yale use [Duo Mobile](https://duo.com/product/multi-factor-authentication-mfa/duo-mobile-app), requiring that you input (if you haven’t recently) a 6-digit code from your phone in addition to your password.

Read up on two-factor authentication at [searchsecurity.techtarget.com/definition/two-factor-authentication](https://searchsecurity.techtarget.com/definition/two-factor-authentication).

1. (1 point.) In no more than one sentence, what type of authentication factor is Duo Mobile?

2. (2 points.) Note that “using two factors from the same category  doesn’t constitute 2FA.” In no more than three sentences, why is using  two factors from different categories considered more secure than using  two factors from the same category?

3. (2 points.) In no more than three sentences, what’s a downside of  requiring users to use an authentication factor like Duo Mobile?

4. (3 points.) Before smart phones were omnipresent, 2FA was more  often implemented with key fobs (aka hardware tokens) that users carried on keychains, like the below. Every 60 seconds or so, those key fobs  would display a different 6-digit code that users could input into an  app or website. But those key fobs didn’t have internet access. In no  more than three sentences, how might the app or website know what code  to expect at any given time?

   ![RSA SecurID token](https://cs50.harvard.edu/college/2022/spring/test/duo/RSA_SecurID_Token_Old.jpg)

   Source: [en.wikipedia.org/wiki/RSA_SecurID](https://en.wikipedia.org/wiki/RSA_SecurID)