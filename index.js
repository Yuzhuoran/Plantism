const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.senfNotification = functions.database.ref(
	'userInfo/{uid}/now/{sensorId}')
	.onWrite((change, context) => {
		console.log('push notification events');
		
		const uid = context.params.
	})