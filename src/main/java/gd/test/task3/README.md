Given a stream (infinite array) of

` {
    timestamp: <timestamp in seconds>,
    name: <some string>
    value: <a numeric value>
    tags: [<strings>]
}`

Do the following:
Design/develop program that remembers max(value) for every 'name' for the past ten minutes only.

Conditions: No database or external storage.