import "./Map.css"
import React, {useEffect, useState} from 'react';
import {MapContainer, TileLayer, Marker, Popup, Polyline} from 'react-leaflet'
import axios from "axios";
import Route from './mockData.json'
import MockRoute from './mockRoute.json'

export default function Map(routesData) {

    let startPoint = routesData.routeData.properties.params.agents[0].start_location;
    let shipments = routesData.routeData.properties.params.shipments;
    let agentDeliveries = routesData.routeData.features;
    let deliveriesPerAgent = Array(agentDeliveries.length);


        // Find which orders the agent has
        for (let j = 0; j < agentDeliveries.length; j++) {
            deliveriesPerAgent[j] = [];
            for (let agent = 0; agent < agentDeliveries[j].properties.actions.length; agent++) {
                if (agentDeliveries[j].properties.actions[agent].type === "delivery") {
                    let waypointName = agentDeliveries[j].properties.actions[agent].shipment_id;
                    deliveriesPerAgent[j].push(waypointName)
                }
            }
        }

        let waypoints = startPoint[1].toString() + "," + startPoint[0].toString() + "|";

        let queries = Array(agentDeliveries.length);

        for (j = 0; j < queries.length; ++j) {
            queries[j] = waypoints;
            for (let a = 0; a < deliveriesPerAgent[j].length; a++) {
                for (var g = 0; g < shipments.length; ++g) {
                   if (shipments[g].id ===  deliveriesPerAgent[j][a]) {
                       var waypointString = shipments[g].delivery.location[1].toString() + "," + shipments[g].delivery.location[0].toString();
                       queries[j] += waypointString;
                   }
                }
                if (a < deliveriesPerAgent[j].length - 1) queries[j] += "|"
            }
        }



    var api = true;

        //console.log(queries)
    const urls = Array(queries.length)

    for (j = 0; j < urls.length; ++j) {
        urls[j] = `https://api.geoapify.com/v1/routing?waypoints=${queries[j]}&mode=drive&apiKey=4c4e2fa3ec4c4ddeb959d2686d3b8c66`
    }


    const [list, setList] = useState([]);

    const getRoutes = async(url) => {
        for (let s of url) {
            const resp = await fetch(s)
            const d = await resp.json()
            setList(list => [...list, d])
        }
    }

    useEffect(() => {
        if (api) {
            getRoutes(urls)
        }
    }, []);

    //console.log(polylines)
    if (list.length === urls.length || !api) {
        var polylines = []
        if (api) {
            polylines = Array(list.length)
            for (var p = 0; p < list.length; p++) {
                polylines[p] = list[p].features[0].geometry.coordinates;
            }
            //console.log("API ON")
        }
        else {
            polylines = Array(1)
            polylines[0] = MockRoute.features[0].geometry.coordinates;
            //console.log("API OFF")
        }

        const swappedPolyline = new Array(polylines.length);

        for (var y = 0; y < swappedPolyline.length; y++) {
            swappedPolyline[y] = new Array(polylines[y].length)

            for (var x = 0; x < swappedPolyline[y].length; x++) {
                swappedPolyline[y][x] = [];
            }

            for (var i = 0; i < polylines[y].length; i++) {
                for (var j = 0; j < polylines[y][i].length; j++) {
                    var oldCoordinate = polylines[y][i][j];
                    swappedPolyline[y][i][j] = [oldCoordinate[1], oldCoordinate[0]];
                }
            }
        }

        return (
            <MapContainer center={[41.389302, 2.113257]} zoom={15} scrollWheelZoom={true}>
                <TileLayer
                    url="https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png"
                    subdomains='abcd'
                    maxZoom="20"
                />
                <Marker position={[startPoint[1], startPoint[0]]}>
                    <Popup>
                        <b> Starting Point </b>
                    </Popup>
                </Marker>
                {
                    shipments.map(ship =>
                        <Marker
                            key={ship.id}
                            position={[ship.delivery.location[1], ship.delivery.location[0]]}
                        >
                            <Popup>
                                <b>
                                    Delivery {ship.id.split("_").pop()}
                                </b>
                            </Popup>
                        </Marker>
                    )
                }
                {
                    swappedPolyline.map( route =>
                        <Polyline positions={route}/>
                    )
                }
            </MapContainer>
        );
    }
    else {
        return (
            <MapContainer center={[41.389302, 2.113257]} zoom={15} scrollWheelZoom={true}>
                <TileLayer
                    url="https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png"
                    subdomains='abcd'
                    maxZoom="20"
                />
                <Marker position={[startPoint[1], startPoint[0]]}>
                    <Popup>
                        <b> Starting Point </b>
                    </Popup>
                </Marker>
                {
                    shipments.map(ship =>
                        <Marker
                            key={ship.id}
                            position={[ship.delivery.location[1], ship.delivery.location[0]]}
                        >
                            <Popup>
                                <b>
                                    Delivery {ship.id.split("_").pop()}
                                </b>
                            </Popup>
                        </Marker>
                    )
                }
            </MapContainer>
        )
    }
}