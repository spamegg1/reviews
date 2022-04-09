<!DOCTYPE html>

<html lang="en">
    <head>
        <link rel="stylesheet"
              href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@9.17.1/build/styles/default.min.css">
        <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@9.17.1/build/highlight.min.js"></script>
        <script src="//cdn.jsdelivr.net/npm/highlightjs-line-numbers.js@2.7.0/dist/highlightjs-line-numbers.min.js"></script>
        <style>

            /* for block of numbers */
            .hljs-ln-line.hljs-ln-numbers {
                -webkit-touch-callout: none;
                -webkit-user-select: none;
                -khtml-user-select: none;
                -moz-user-select: none;
                -ms-user-select: none;
                user-select: none;

                text-align: center;
                color: #ccc;
                border-right: 1px solid #CCC;
                vertical-align: top;
                padding-right: 5px;
            }

            /* for block of code */
            .hljs-ln-line.hljs-ln-code {
                padding-left: 12px;
            }
        </style>

        <title>create_solved.c</title>
    </head>
    <body>
        <pre>
            <code style="background-color: white;"></code>
        </pre>
    </body>
    <script>
        fetch("https://s3.amazonaws.com/cdn.cs50.net/2022/spring/classes/4/create_solved.c?AWSAccessKeyId=AKIAJWDAOEC2ETXV2L6A\u0026Signature=Wk91i5Pq3LXLPuor15iDryUHaDM%3D\u0026Expires=1649599325")
        .then((response) => {
            if (response.status === 200) {
                return response.text()
            }

            document.querySelector("body").textContent = response.statusText
            return Promise.reject()
        })
        .then((code) => {
            const block = document.querySelector("code")
            block.textContent = code
            hljs.highlightBlock(block)
            hljs.lineNumbersBlock(block)
        })
    </script>
</html>