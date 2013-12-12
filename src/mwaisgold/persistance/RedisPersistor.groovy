package mwaisgold.persistance

import mwaisgold.domain.Room
import mwaisgold.domain.User
import redis.clients.jedis.Jedis

/**
 * User: mwaisgold
 * Date: 12/12/13
 * Time: 1:39 AM 
 */

class RedisPersistor {

    Jedis jedis

    RedisPersistor() {
        jedis = new Jedis("localhost")
    }

    def getAllUsers(){
        jedis.keys("users:*").collect {
            def map = jedis.hgetAll(it)
            map.isAnonymus = Boolean.parseBoolean(map.isAnonymus)
            new User(map)
        }
    }

    def getAllRooms(){
        jedis.keys("rooms:*").collect {
            jedis.hgetAll(it)
        }
    }

    def saveUser(User user){
        jedis.hmset("users:$user.userName", [
                userName: user.userName,
                isAnonymus: user.isAnonymus.toString(),
                password: user.password
        ])
    }

    def saveRoom(Room room){
        def map = [
                name: room.name,
                creator: room.creator.userName
        ]
        if (room.password) map.password = room.password
        jedis.hmset("rooms:$room.name", map)
    }
}
