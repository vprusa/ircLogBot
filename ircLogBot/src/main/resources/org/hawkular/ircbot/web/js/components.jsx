var LogEntry = React.createClass({
	render: function() {
		return (
			<tr>
				<td id={this.props.id}><a href={'#' + this.props.id}>{this.props.time}</a></td>
				<td><b>{this.props.name}</b></td>
				<td dangerouslySetInnerHTML={{__html: this.props.message}} />
			</tr>
			);
	}
});

var LogTable = React.createClass({

	getLogEntries: function() {
		var rows = [];
		if(this.props.entries && this.props.entries.length > 0) {
			var searchPhrase = this.props.searchPhrase;
			this.props.entries.forEach(function(entry, id){
				if (!searchPhrase || entry.message.indexOf(searchPhrase) > -1) {
					rows.push(<LogEntry time={entry.time} name={entry.name} message={entry.message} id={id} />);
				}
			});
		}
		return rows;
	},

	render: function() {
		var rows = this.getLogEntries();
		return (
			<table>
				<tbody>
					{rows}
				</tbody>
			</table>
			);
	}
});

var previousEntries;

function noData() {
	React.render(
		<h2>no data</h2>,
		document.getElementById('myDiv')
		);
}

function getData(searchPhrase) {
	console.log('getting data for search text: ' + searchPhrase);
	$.ajax({
		type: 'GET',
		url: 'latestIrcLog.json',
		isLocal: true,
		dataType: 'text',
		ifModified: !!searchPhrase,
		success: function(jsonString) {
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
			React.render(
				<LogTable entries={entries} searchPhrase={searchPhrase} />,
				document.getElementById('myDiv')
			);
		},
		error: function(jqXHR, textStatus, errorThrown){
			console.error(textStatus);
			noData();
		}
	});
};