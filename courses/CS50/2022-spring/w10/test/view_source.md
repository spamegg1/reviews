# [View Source](https://cs50.harvard.edu/college/2022/spring/test/source/#view-source)

[watch](https://www.youtube.com/embed/NxJjLWa9R2g?modestbranding=0&amp;rel=0&amp;showinfo=0&amp;start=428)

On October 14, 2021, the St. Louis Post-Dispatch [reported](https://www.stltoday.com/news/local/education/missouri-teachers-social-security-numbers-at-risk-on-state-agencys-website/article_f3339700-ece0-54a1-9a45-f300321b7c82.html) that the “Social Security numbers of school teachers, administrators  and counselors across Missouri were vulnerable to public exposure due to flaws on a website maintained by the state’s Department of Elementary  and Secondary Education.” If unfamiliar, a [Social Security number](https://en.wikipedia.org/wiki/Social_Security_number) is a nine-digit number, ordinarily formatted as  ###-##-####, assigned to individuals in the United States, considered  sensitive, personally identifiable information. “Though no private  information was clearly visible nor searchable on any of the web pages,  the newspaper found that teachers’ Social Security numbers were  contained in the HTML source code of the pages involved.” A professor at the University of Missouri-St. Louis [verified](https://cdn.arstechnica.net/wp-content/uploads/2021/10/Litigation-Hold-and-Demand.pdf) the security flaw.

The journalist was subsequently [characterized](https://oa.mo.gov/commissioners-office/news/state-missouri-addresses-data-vulnerability) by the state as a “hacker,” with the state’s governor [threatening](https://www.stltoday.com/news/local/govt-and-politics/parson-issues-legal-threat-against-post-dispatch-after-database-flaws-exposed/article_93f4d7d6-f792-5b1b-b556-00b5cac23af3.html) legal action. Not everyone agrees, with a member of the governor’s own party [responding](https://twitter.com/tonylovasco/status/1448672694065668105), “It’s clear the Governor’s office has a fundamental misunderstanding of both web technology and industry standard procedures for reporting  security vulnerabilities. Journalists responsibly sounding an alarm on  data privacy is not criminal hacking.”

The [department’s website](https://apps.dese.mo.gov/) was, for several weeks thereafter, “down for maintenance.”

1. (3 points.) In no more than three sentences, argue, in technical  terms, why viewing the source code of a web page should not, in fact, be considered hacking.

1. (3 points.) In no more than three sentences, hypothesize in  technical terms how teachers’ Social Security numbers (SSNs) could have  been in pages’ HTML but not be visible to visitors (unless they viewed  the pages’ source).

------

Per the St. Louis Post-Dispatch’s [report](https://www.stltoday.com/news/local/education/missouri-teachers-social-security-numbers-at-risk-on-state-agencys-website/article_f3339700-ece0-54a1-9a45-f300321b7c82.html), “The newspaper delayed publishing this report to give the department  time to take steps to protect teachers’ private information, and to  allow the state to ensure no other agencies’ web applications contained  similar vulnerabilities,” which is an example of [responsible disclosure](https://en.wikipedia.org/wiki/Responsible_disclosure).

1. (2 points.) Suppose that the newspaper had instead published its  report right away, without notifying Department of Elementary and  Secondary Education (DESE) first. In no more than three sentences, in  what sense might that have been irresponsible?

1. (2 points.) Suppose that the newspaper had waited even longer to  publish its report, to give DESE even more time. In no more than three  sentences, in what sense might that have been irresponsible?