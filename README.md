<h1>📝 Table Editor</h1>

Test task project created for the purposes of application to <b> JetBrains Internship ("Low code Table Data preprocessing").</b> <br/>
Date of creation: <b>May, 2024</b>. <br/>
For original version (without early development of type support), consider checking out: <a href="https://github.com/LukaNedimovic/table_editor/tree/1fc7ecbd9728186ef982727d89a55e16369a7b87">Double-only version</a>.

<h2> 🚀 Quick Start </h2>
<b>Table Editor</b> can be compiled and ran using provided <b>gradlew</b>:
<pre>
<code>git clone https://github.com/LukaNedimovic/table_editor.git
cd table_editor
./gradlew compileJava run</code>
</pre>

<h2> 🛠️ Basic Features </h2>
<ol>
  <li> <b><code>Constant values</code> </b> 
    <ol>
      <li> <b> <code>Numerical values</code> </b> - e.g. <code>2.5</code>, <code>0.09</code>, <code>3.14</code> </li>
      <li> <b> <code>String values</code> </b> - e.g. <code>"apple" (apple)</code>, <code>"this is not a function"</code>.</li>
      <li> <b> <code>Boolean values</code> </b> -  i.e. <code>True</code> and <code>False</code>.</li>
    </ol>
    
  <li> <b> <code>Cell References</code> </b> - e.g. <code>B2</code>; cell ranges (e.g. <code>B2:D4</code>) are not supported yet as functional, but their recognition in parsing is and can be easily implemented. </li>
  <li> <b> <code>Precedence</code> </b>     - i.e. every operation and function has defined precedence and it is flexible to change.
  <li> <b> <code>Parentheses</code> </b>     - i.e. <code>(</code> and <code>)</code>, with nesting </li>
  <li> <b> <code>Operations</code> </b> 
    <ol>
      <li> <b><code>Unary Operations</code></b> - e.g. <code>+ (identity)</code>, <code>- (negation).</code> Only prefix operations, as of now. </li>
      <li> <b><code>Binary Operations</code></b> - e.g. <code>+ (addition)</code>, <code>- (subtraction)</code>, <code>* (multiplication)</code>, <code>/ (division)</code>, <code>^ (exponentiation)</code>, <br/> <code>% (modulo)</code>, <code>< (less than)</code>, <code>> (greater than)</code> </li>
    </ol>
  </li>
  <li> <b><code>Named Functions</code></b> 
    <ol>
      <li> <b><code>Nullary Functions</code> </b> - e.g. <code>e()</code>, <code>pi()</code> </li>
      <li> <b><code>Unary Functions</code> </b> - e.g. <code>sqrt(x)</code>, <code>abs(x)</code> </li>
      <li> <b><code>Binary Functions</code> </b> - e.g. <code>pow(x, y)</code>, <code>gcd(x, y)</code>, <code>lcm(x, y)</code>, <code>min(x, y)</code>, <code>max(x, y)</code>, <code>ifeq(x, y)</code></li>
    </ol>
  </li>
</ol>

<h2> 🔍 Complex Features </h2>
<b>Table Editor</b> was created with <b>flexibility</b> in mind, with minimalistic (but clean!) UI to guide the user through. <br/>
Even though it is a small project, there are some cool features, such as:
<ul>
  <li> <b><code>New operations</code></b> - Table Editor provides you with tokenizer & parser that are not restricted to the aforementioned operations. It is easy to add new operations (both unary and binary), and define their functionality within just a few lines of code. </li>
  <li> <b><code>New functions</code></b> - Functions are also very simple to add - interface created lets you create functions with any number of arguments, accepting anything as their arguments and having as complex functionality as you wish. </li>
  <li> <b><code>Scalability</code></b> - Table Editor provides scalable code that can generalize onto various datatypes within the table. Exemplary functions are oriented towards <i>Double</i> data type (i.e. <i>DTypeDouble</i>), however one can add various functions and even extend singular function to be capable of accepting different kinds of operators and return different types. </li>
  <li> <b><code>Event handlers</code></b> - One-click row and column selection can be scaled into something greater. </li>
</ul>

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

<tr>
  <td> <code>=ifeq(pow(5, 2), 25)</code> </td>
  <td> <code>False</code> </td>
  <td> <code>pow(5, 2)</code> returns <code>25.0</code>, which is not the same as <code>25</code>.</td>
</tr>

<tr>
  <td> <code>=ifeq(2 < 10, 1.0)</code> </td>
  <td> <code>False</code> </td>
  <td> <code>2 < 10</code> evaluates to <code>True</code>, which can't be compared with DTypeDouble value of <code>1.0</code>.</td>
</tr>

<tr>
  <td> <code>=("abc" < "dddd") + 10</code> </td>
  <td> <code>11</code> </td>
  <td> <code>("abc" < "dddd")</code> evaluates to <code>True</code>, which can be added to the DTypeInteger value of <code>10</code> to get <code>11</code>!</td>
</tr>

<tr>
  <td> <code>=ifeq("w" * 3 + "123", "www123") * 5</code></td>
  <td> <code>5</code> </td>
  <td> String concatenation and repetition of N times is supported, too! 
<pre>
<code>=ifeq("w" * 3 + "123", "www123") * 5   
=ifeq("www" + "123", "www123") * 5
=ifeq("www123", "www123") * 5
=True * 5
=5</code>
</pre>
  </td>
</tr>
</table>

<h2> 🔥 Latest Modifications </h2>
<b>Tests</b> have been added for operations (type-wise combinations), functions, and general expressions! Make sure to check them out:
<pre><code>gradle test</code></pre>
<b>Table Editor</b> now supports <code>DTypeString</code> - used for manipulation of String literals! <br/>
<b>Table Editor</b> now supports <code>DTypeBoolean</code> - used for manipulation of Boolean values (<code>True</code> / <code>False</code>)! 

<h2> 📅 TODO </h2>
<ul>
  <li> <code><b> Strongly typed functions</b></code> - Functions, as of now, convert certain types among each other. They should not do that - strongly typed function parameters need to be easily implementable </li>
  <li> <code><b> List DType</b></code> - Type that contains list of some type. It would be useful for functions with unfixed number of arguments. </li>
</ul>
