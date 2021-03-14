db = connect("169.51.194.19:30017");

use admin
db.auth('admin', 'pa55w0rd')

db = db.getSiblingDB('Order')

db.createUser({
    user: 'orderdbuser', 
    pwd: 'orderdbuserpwd', 
    roles: [
        { role: 'dbOwner', 
        db: 'Order' }
    ] 
})
