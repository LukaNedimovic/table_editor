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
      <li> <b><code>Unary Operations</code></b> - e.g. <code>+ (identity)</code>, <code>- (negation).</code> Only prefix operations, as of now. </li>
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
</ul>

<h2> 🚀 Quick Start </h2>
<b>Table Editor</b> can be compiled and ran using <b>gradle</b>:
<pre>
<code>git clone https://github.com/LukaNedimovic/table_editor.git
cd table_editor
./gradlew compileJava run</code>
</pre>

<h2>💡 Examples </h2>
<table>
<tr>
  <td>Expression</td>
  <td>Evaluation</td>
  <td>Comment</td>
</tr>
<tr>
  <td> <code>=sqrt(abs(-16))</code> </td>
  <td> <code>4.0</code> </td>
  <td> Function nesting is possible. </td>
</tr>  
  
<tr>
  <td> <code>=pow(max(1++1, ---1000), 3)</code> </td>
  <td> <code>8.0</code> </td>
  <td> Same unary / binary operations can be concatenated. </td>
</tr>

<tr>
  <td> <code>=pow(min(B1^2, abs(-1000)), 3)</code> </td>
  <td> <code>64.0</code> </td>
  <td> For cell value <code>B1 = 4</code> </td>
</tr>


<tr>
  <td> <code>=5 + e() - pi()^2</code> </td>
  <td> <code>-2.1513225726303133</code> </td>
  <td> Nullary functions can used for constants, and are treated as numerical values </td>
</tr>

</table>
