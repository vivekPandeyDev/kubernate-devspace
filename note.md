<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>K3d + DevSpace + Kubernetes Workflow — user-service</title>
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
    ul {
      margin-top: 0;
    }
  </style>
</head>
<body>

<h2>🚀 K3d + DevSpace + Kubernetes Workflow — <code>user-service</code></h2>

<h3>🧩 K3d Image Import</h3>

<p>K3d runs Kubernetes clusters inside Docker.  
To make your locally built Docker images available to the K3d cluster, you must import them manually.</p>

<pre><code class="language-bash">
k3d image import user-service:1.0.0 -c devcluster
</code></pre>

<p>This command:</p>
<ul>
  <li>Imports the local Docker image <code>user-service:1.0.0</code> into the K3d cluster named <code>devcluster</code>.</li>
  <li>Ensures that Kubernetes Pods can pull and run the image directly without pushing to a remote registry.</li>
</ul>

<hr>

<h3>🧠 Running <code>user-service</code> in DevSpace</h3>

<p>When using <strong>DevSpace</strong>, you can run your Spring Boot service in a continuous development mode inside the dev container:</p>

<pre><code class="language-bash">
./gradlew bootRun --args='--spring.docker.compose.enabled=false' --continuous
</code></pre>

<p>This disables Docker Compose (since you’re running inside DevSpace) and enables hot reloading of source code.</p>

<hr>

<h3>⚙️ Development Workflow</h3>

<table>
  <tr>
    <th>Step</th>
    <th>Action</th>
    <th>Description</th>
  </tr>
  <tr>
    <td>1️⃣</td>
    <td><code>./gradlew classes --continuous</code></td>
    <td>Run this in your local IDE terminal to continuously rebuild class files when source code changes.</td>
  </tr>
  <tr>
    <td>2️⃣</td>
    <td><code>devspace dev</code></td>
    <td>Starts the DevSpace development environment connected to your K3d cluster.</td>
  </tr>
  <tr>
    <td>3️⃣</td>
    <td><code>./gradlew bootRun --args='--spring.docker.compose.enabled=false' --continuous</code></td>
    <td>Run this inside the DevSpace terminal to start the Spring Boot app and reflect live code updates.</td>
  </tr>
</table>

<hr>

<h3>🔁 Re-deploying the User Service</h3>

<p>After importing a new image or updating code, you can trigger a <strong>rolling restart</strong> of the <code>user-service</code> Deployment to apply changes:</p>

<pre><code class="language-bash">
kubectl rollout restart deployment user-service
</code></pre>

<p>This command tells Kubernetes to:</p>
<ul>
  <li>Restart all Pods under the <code>user-service</code> Deployment.</li>
  <li>Use the latest imported Docker image (<code>user-service:1.0.0</code>).</li>
  <li>Perform a <strong>rolling update</strong> — creating new Pods and gracefully removing the old ones.</li>
</ul>

<h3>🧩 What Happens Internally</h3>
<table>
  <tr>
    <th>Step</th>
    <th>Process</th>
  </tr>
  <tr>
    <td>1️⃣</td>
    <td>Kubernetes updates the Deployment’s <code>podTemplate</code> with a new timestamp annotation.</td>
  </tr>
  <tr>
    <td>2️⃣</td>
    <td>New Pods are created using the latest image and configuration.</td>
  </tr>
  <tr>
    <td>3️⃣</td>
    <td>Old Pods terminate gracefully once the new Pods are healthy.</td>
  </tr>
  <tr>
    <td>4️⃣</td>
    <td>Rollout completes successfully with minimal downtime.</td>
  </tr>
</table>

<hr>

<h3>📋 Helpful Commands</h3>
<table>
  <tr>
    <th>Command</th>
    <th>Purpose</th>
  </tr>
  <tr>
    <td><code>k3d image import user-service:1.0.0 -c devcluster</code></td>
    <td>Make local Docker image available inside the K3d cluster.</td>
  </tr>
  <tr>
    <td><code>kubectl rollout restart deployment user-service</code></td>
    <td>Trigger rolling restart using the new image.</td>
  </tr>
  <tr>
    <td><code>kubectl rollout status deployment user-service</code></td>
    <td>Monitor restart progress until rollout completes.</td>
  </tr>
  <tr>
    <td><code>kubectl get pods</code></td>
    <td>Confirm new Pods are up and running.</td>
  </tr>
</table>

<hr>

<p class="highlight">✅ Summary:</p>
<ul>
  <li>Import your latest image into K3d.</li>
  <li>Run DevSpace to develop live.</li>
  <li>Trigger a Kubernetes rollout restart when code or image updates are ready.</li>
</ul>

</body>
</html>
