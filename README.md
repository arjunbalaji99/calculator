<h1 align="center">Calculator</h1>
<h2 align="center">Authors</h2>
<ul>
  <li>Michael Sheng</li>
  <li>Arjun Balaji</li>
</ul>

<h2 align="center">Project Overview</h2>
<ul>
  <li>The goal was to build a calculator that can parse and evaluate complex expressions – including negative numbers, decimals, order of operations, wide quantity of types of operations.</li>
  <li>Show to use a 3-module architecture to split up the work so that components of work were very easily split – allowed us to work together more easily as well.</li>
</ul>

<h2 align="center">Architecture Description</h2>
<ul>
  <li>
    Three Main Modules: 
    <ul>
      <li>Engine – Does all math-based work – takes in a singular expression and returns the result. </li>
      <li>UI – Does all front facing work – takes in input from user, displays any expressions and results it is given.</li>
      <li>Translator – Does all parsing work – takes in an input from the UI, cleans it up into singular statements, and sends them to the engine one by one to calculate the result.</li>
    </ul>
  </li>
</ul>

<h2 align="center">Instructions to Run</h2>
<ul>
  <li>(temporary) Go to UI.java and run <code>main()</code></li>
</ul>

<h2 align="center">Citations</h2>
<ul>
  <li>Java Swing documentation to figure out how to use Swing.</li>
</ul>

