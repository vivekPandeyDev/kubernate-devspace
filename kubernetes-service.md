<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Kubernetes Service Explained — movie-service</title>
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

<h2>🎬 Kubernetes Service Example — <code>movie-service</code></h2>

<pre><code class="language-yaml">
apiVersion: v1
kind: Service
metadata:
  name: movie-service
  labels:
    app: movie-service               # 👈 label for organizing Service resources
spec:
  selector:
    app: movie-service               # 👈 finds Pods with this label
  ports:
    - protocol: TCP
      port: 80                       # 👈 port exposed inside cluster
      targetPort: 8080               # 👈 port inside the Pod (container port)
  type: ClusterIP                    # 👈 internal access only (default)
</code></pre>

<h3>🔍 What Happens</h3>
<ul>
  <li><code>kind: Service</code> → Creates a stable networking endpoint inside your cluster.</li>
  <li><code>metadata.name</code> → The name of the Service (<code>movie-service</code>).</li>
  <li><code>spec.selector</code> → Tells the Service <strong>which Pods to send traffic to</strong>.</li>
  <li><code>ports</code> → Defines how traffic is routed:</li>
  <ul>
    <li><strong>port</strong> → the port the Service exposes.</li>
    <li><strong>targetPort</strong> → the port on the Pod (your container) that receives traffic.</li>
  </ul>
  <li><code>type: ClusterIP</code> → Default type — internal access only.</li>
</ul>

<h3>🧠 Key Concepts</h3>
<table>
  <tr>
    <th>Concept</th>
    <th>Description</th>
  </tr>
  <tr>
    <td><strong>Service</strong></td>
    <td>Provides a stable way to reach your Pods.</td>
  </tr>
  <tr>
    <td><strong>Pod IPs</strong></td>
    <td>Change when Pods restart — not reliable for communication.</td>
  </tr>
  <tr>
    <td><strong>Service IP</strong></td>
    <td>Fixed virtual IP that routes traffic to matching Pods.</td>
  </tr>
  <tr>
    <td><strong>Selector</strong></td>
    <td>Matches Pods using labels (must match Deployment’s Pod labels).</td>
  </tr>
  <tr>
    <td><strong>Port vs TargetPort</strong></td>
    <td>External vs internal ports.</td>
  </tr>
  <tr>
    <td><strong>Type: ClusterIP</strong></td>
    <td>Internal communication within the cluster.</td>
  </tr>
</table>

<h3>✅ Matching with Deployment</h3>
<p>Your <strong>Deployment</strong> created Pods with this label:</p>
<pre><code class="language-yaml">app: movie-service</code></pre>

<p>Your <strong>Service</strong> uses this selector:</p>
<pre><code class="language-yaml">
selector:
  app: movie-service
</code></pre>

<p><span class="highlight">✅ This means:</span> The Service automatically discovers and load-balances traffic across all Pods from that Deployment.</p>

<h3>⚠️ If Labels Don’t Match</h3>

<pre><code class="language-yaml">
spec:
  selector:
    app: auth-service   # ❌ mismatch
</code></pre>

<p><strong class="error">Consequence:</strong></p>
<ul>
  <li>The Service won’t find any Pods.</li>
  <li>Traffic sent to the Service goes nowhere, even though Pods are running.</li>
</ul>

<h3>🌐 Service Types Overview</h3>

<table>
  <tr>
    <th>Type</th>
    <th>Description</th>
    <th>Accessibility</th>
    <th>Example Use Case</th>
  </tr>
  <tr>
    <td><code>ClusterIP</code></td>
    <td>(Default) Internal-only access via cluster network.</td>
    <td>Inside cluster only</td>
    <td>Microservice → Microservice calls</td>
  </tr>
  <tr>
    <td><code>NodePort</code></td>
    <td>Exposes Service on <code>&lt;NodeIP&gt;:&lt;NodePort&gt;</code> for external access.</td>
    <td>Outside cluster</td>
    <td>Quick testing or dev access</td>
  </tr>
  <tr>
    <td><code>LoadBalancer</code></td>
    <td>Creates an external load balancer (via cloud provider).</td>
    <td>Public access</td>
    <td>Production APIs</td>
  </tr>
  <tr>
    <td><code>ExternalName</code></td>
    <td>Maps to an external DNS name (no selector).</td>
    <td>External target</td>
    <td>Proxying to an external service</td>
  </tr>
</table>

<h3>🧩 Deployment + Service Together</h3>

<table>
  <tr>
    <th>Resource</th>
    <th>Purpose</th>
    <th>Key Matching Label</th>
  </tr>
  <tr>
    <td>Deployment</td>
    <td>Creates and manages Pods</td>
    <td><code>app: movie-service</code></td>
  </tr>
  <tr>
    <td>Service</td>
    <td>Routes traffic to those Pods</td>
    <td><code>app: movie-service</code></td>
  </tr>
</table>

</body>
</html>
