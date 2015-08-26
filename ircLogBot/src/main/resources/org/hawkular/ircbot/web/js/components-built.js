'use strict';

var LogEntry = React.createClass({
	displayName: 'LogEntry',

	render: function render() {
		return React.createElement(
			'tr',
			null,
			React.createElement(
				'td',
				{ id: this.props.id },
				React.createElement(
					'a',
					{ href: '#' + this.props.id },
					this.props.time
				)
			),
			React.createElement(
				'td',
				null,
				React.createElement(
					'b',
					null,
					this.props.name
				)
			),
			React.createElement('td', { dangerouslySetInnerHTML: { __html: this.props.message } })
		);
	}
});

var LogTable = React.createClass({
	displayName: 'LogTable',

	getLogEntries: function getLogEntries() {
		var rows = [];
		if (this.props.entries && this.props.entries.length > 0) {
			var searchPhrase = this.props.searchPhrase;
			this.props.entries.forEach(function (entry, id) {
				if (!searchPhrase || entry.message.indexOf(searchPhrase) > -1) {
					rows.push(React.createElement(LogEntry, { time: entry.time, name: entry.name, message: entry.message, id: id }));
				}
			});
		}
		return rows;
	},

	render: function render() {
		var rows = this.getLogEntries();
		return React.createElement(
			'table',
			null,
			React.createElement(
				'tbody',
				null,
				rows
			)
		);
	}
});

var previousEntries;

function noData() {
	React.render(React.createElement(
		'h2',
		null,
		'no data'
	), document.getElementById('myDiv'));
}

function getData(searchPhrase) {
	console.log('getting data for search text: ' + searchPhrase);
	$.ajax({
		type: 'GET',
		url: 'latestIrcLog.json',
		isLocal: true,
		dataType: 'text',
		ifModified: !!searchPhrase,
		success: function success(jsonString) {
			var entries;
			if (jsonString) {
				// ends with comma
				if (jsonString.indexOf(',', jsonString.length - 2) !== -1) {
					var data = $.parseJSON(jsonString.substring(0, jsonString.length - 2) + ']}');
					entries = data.entries.reverse();
					previousEntries = entries;
				} else {
					console.error('no comma at the end of the json file');
					noData();
					return;
				}
			} else {
				entries = previousEntries;
			}

			// entries = searchPhrase ? _.filter(entries, function(entry){
			// 	return entry.message.indexOf(searchPhrase) > -1;
			// }) : entries;
			React.render(React.createElement(LogTable, { entries: entries, searchPhrase: searchPhrase }), document.getElementById('myDiv'));
		},
		error: function error(jqXHR, textStatus, errorThrown) {
			console.error(textStatus);
			noData();
		}
	});
};

