# Connectwise

This is a simple Clojure library for Connectwise Manage

[![Clojars Project](https://img.shields.io/clojars/v/org.clojars.zpeters/connectwise.svg)](https://clojars.org/org.clojars.zpeters/connectwise)
[![cljdoc badge](https://cljdoc.org/badge/org.clojars.zpeters/connectwise)](https://cljdoc.org/d/org.clojars.zpeters/connectwise/CURRENT)
![Clojure CI](https://github.com/zpeters/connectwise-clojure/workflows/Clojure%20CI/badge.svg)

## Configuration
The following env vars need to be set:
- connectwise-public-key 
- connectwise-private-key
- connectwise-company-id
- connectwise-client-id

### Testing
Create a test-config.edn file in the root directory

### Sample edn file
```
{
 :connectwise-public-key "YOUR PUBLIC KEY"
 :connectwise-private-key "YOUR PRIVATE KEY"
 :connectwise-company-id "your company"
 :connectwise-client-id "YOUR CLIENT ID"
 }
```
## Usage
*Get System info*
`(get-connectwise "/system/info")`

*Get a member named foobar*
`(get-connectwise "/system/members" {:conditions "identifier='foobar'"})`

*Get all tickets for company XYZ*
`(get-all-connectwise "/service/tickets" {:conditions "company/name='XYZ'"})`

*Get the second page of results*
`(get-connectwise "/service/tickets" {:conditions "company/name='XYZ'" :page 2})`

(See the official [connectwise api docs] (https://developer.connectwise.com/Products/Manage/Developer_Guide) for more details on parameters and formatting)

## License

Copyright © 2020

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
