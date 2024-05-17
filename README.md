<h1>📝 Table Editor</h1>

Test task project created for the purposes of application to <b> JetBrains Internship ("Low code Table Data preprocessing").</b> <br/>
Date of creation: <b>May, 2024</b> 

<h2> 🛠️ Basic Features </h2>
<ol>
  <li> <b><code>Constant values</code> </b> - e.g. <code>2.5</code>, <code>0.09</code>, <code>3.14</code> </li>
  <li> <b><code>Cell References</code> </b> - e.g. <code>B2</code>; cell ranges (e.g. <code>B2:D4</code>) are not supported yet as functional, but their recognition in parsing is and can be easily implemented. </li>
  <li> <b><code>Precedence</code> </b>     - i.e. every operation and function has defined precedence and it is flexible to change.
  <li> <b><code>Parentheses</code> </b>     - i.e. <code>(</code> and <code>)</code>, with nesting </li>
  <li> <b><code>Operations</code> </b> 
    <ol>
      <li> <b><code>Unary Operations</code></b> - e.g. <code>+ (identity)</code>, <code>- (negation)</code> </li>
      <li> <b><code>Binary Operations</code></b> - e.g. <code>+ (addition)</code>, <code>- (subtraction)</code>, <code>* (multiplication)</code>, <code>/ (division)</code>, <code>^ (exponentiation)</code> </li>
    </ol>
  </li>
  <li> <b><code>Named Functions</code></b> 
    <ol>
      <li> <b><code>Nullary Functions</code></b> - e.g. <code>e()</code>, <code>pi()</code> </li>
      <li> <b><code>Unary Functions</code></b> - e.g. <code>sqrt(x)</code>, <code>abs(x)</code> </li>
      <li> <b><code>Binary Functions</code></b> - e.g. <code>pow(x, y)</code>, <code>min(x, y)</code>, <code>max(x, y)</code> </li>
    </ol>
  </li>
</ol>

<h2> 🔍 Complex Features </h2>
<b>Table Editor</b> was created with <b>flexibility</b> in mind, with minimalistic (but clean!) UI to guide the user through. <br/>
Even though it is a small project, there are some cool features, such as:
<ul>
  <li> <b><code>New operations</code></b> - Table Editor provides you with tokenizer & parser that are not restricted to the aforementioned operations. It is easy to add new operations (both unary and binary), and define their functionality within just a few lines of code. </li>
  <li> <b><code>New functions</code></b> - Functions are also very simple to add - interface created lets you create functions with any number of arguments, accepting anything as their arguments and having as complex functionality as you wish. </li>
  <li> <b><code>Scalability</code></b> - Table Editor provides scalable code that can generalize onto various datatypes within the table. As of now, it only works with decimal numbers (i.e. <b>Double</b>), but the operation's & function's implementations let you quickly add support for data types. </li>
  <li> <b><code>Event handlers</code></b> - One-click row and column selection can be scaled into something greater. </li>
<ul>
