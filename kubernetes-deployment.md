<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Kubernetes Deployment Labels Explained</title>
  <style>
    body {
  font-family: "Segoe UI", Arial, sans-serif;
  background-color: #0d1117;
  color: #e6edf3;
  line-height: 1.6;
  padding: 20px;
}

pre {
background-color: #161b22;
color: #e6edf3;
padding: 16px;
border-radius: 8px;
overflow-x: auto;
border: 1px solid #30363d;
}

code {
background: #21262d;
color: #79c0ff;
padding: 2px 5px;
border-radius: 4px;
font-family: "Consolas", "Courier New", monospace;
}

table {
border-collapse: collapse;
width: 100%;
margin-top: 16px;
background: #161b22;
border: 1px solid #30363d;
border-radius: 6px;
overflow: hidden;
}

th, td {
border: 1px solid #30363d;
padding: 10px 12px;
text-align: left;
}

th {
background-color: #1f6feb;
color: #ffffff;
font-weight: 600;
}

tr:nth-child(even) {
background-color: #0d1117;
}

h2, h3 {
color: #58a6ff;
}

.highlight {
color: #3fb950;
font-weight: bold;
}

.error {
color: #f85149;
font-weight: bold;
}

a {
color: #79c0ff;
text-decoration: none;
}

a:hover {
text-decoration: underline;
}

  </style>
</head>
<body>

<h2>🎬 Kubernetes Deployment Example</h2>

<pre><code class="language-yaml">
apiVersion: apps/v1
kind: Deployment
metadata:
  name: movie-deployment
  labels:
    app: movie-service               # 👈 label on the deployment itself
spec:
  selector:
    matchLabels:
      app: movie-service             # 👈 selects pods with this label
  template:
    metadata:
      labels:
        app: movie-service           # 👈 applied to pods created by this deployment
    spec:
      containers:
      - name: movie-service
        image: alexmercer/movie-service:1.0
</code></pre>

<p><strong>image:</strong> <code>alexmercer/movie-service:1.0</code></p>

<h3>🔍 What Happens</h3>
<ul>
  <li><code>metadata.labels</code> → label on the <strong>Deployment</strong>.</li>
  <li><code>template.metadata.labels</code> → label on the <strong>Pods created</strong> by the Deployment.</li>
  <li><code>spec.selector.matchLabels</code> → tells Kubernetes <strong>which Pods</strong> the Deployment owns.</li>
</ul>

<h3>✅ For This to Work Correctly</h3>
<p>The Pod labels in <code>template.metadata.labels</code> <span class="highlight">must match</span> the selector in <code>spec.selector.matchLabels</code>.</p>
<p>Otherwise — the Deployment <span class="error">won’t find its Pods!</span></p>

<hr>

<h3>❌ Example of Mismatch</h3>

<pre><code class="language-yaml">
spec:
  selector:
    matchLabels:
      app: movie-service
  template:
    metadata:
      labels:
        app: auth-service    # ❌ mismatch
</code></pre>

<p><strong>Consequence:</strong></p>
<ul>
  <li>The Deployment selector is looking for Pods labeled <code>movie-service</code>.</li>
  <li>But it creates Pods labeled <code>auth-service</code>.</li>
  <li>Result: it creates Pods, but <strong>doesn’t manage them</strong> — scaling, rolling updates, etc. won’t work properly.</li>
</ul>

<hr>

<h3>🧠 Analogy</h3>

<table>
  <tr>
    <th>Concept</th>
    <th>Analogy</th>
  </tr>
  <tr>
    <td><code>metadata.labels</code></td>
    <td>The <strong>name tag on your shirt</strong> (“Hi, I’m Alex”)</td>
  </tr>
  <tr>
    <td><code>spec.selector.matchLabels</code></td>
    <td>The <strong>rule someone uses to find you</strong> (“Find everyone wearing a tag that says ‘Alex’”)</td>
  </tr>
</table>

<h3>📘 Summary Table</h3>

<table>
  <tr>
    <th>Concept</th>
    <th>Purpose</th>
    <th>Belongs to</th>
    <th>Must Match?</th>
    <th>Example</th>
  </tr>
  <tr>
    <td><code>metadata.labels</code></td>
    <td>Tags the resource for organization</td>
    <td>Deployment, Service, Pod, etc.</td>
    <td>Not necessarily</td>
    <td><code>app: movie-service</code></td>
  </tr>
  <tr>
    <td><code>spec.selector.matchLabels</code></td>
    <td>Selects target Pods to manage or connect</td>
    <td>Deployment / Service</td>
    <td>✅ Yes — must match Pod labels</td>
    <td><code>app: movie-service</code></td>
  </tr>
  <tr>
    <td><code>template.metadata.labels</code></td>
    <td>Labels added to Pods created by Deployment</td>
    <td>Pod template inside Deployment</td>
    <td>✅ Yes — must match selector</td>
    <td><code>app: movie-service</code></td>
  </tr>
</table>

</body>
</html>
