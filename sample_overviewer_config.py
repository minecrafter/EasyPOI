import json

worlds["World"] = "/home/minecraft/server/world"

with file("/home/minecraft/pois/world.json", "r") as poi_file:
    pois = json.loads(poi_file)

def userMarker(poi):
    if poi['id'] == 'User':
        return (poi['ownerUsername'], poi['description'])

renders["normal"] = {
    "world": "World",
    "title": "crafting.today - overworld",
    "manualpois": pois,
    'markers': [dict(name="User POIs", filterFunction=userMarker, icon="icons/marker_town.png")]
}

outputdir = "/home/minecraft/overviewer-map"