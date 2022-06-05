import React from 'react';
import { ListItem, ListItemText, InputBase, Checkbox, ListItemSecondaryAction, IconButton } from '@mui/material';
import DeleteOutlined from '@mui/icons-material/DeleteOutlined';

class Todo extends React.Component {
	constructor(props) {
		super(props);
		this.state = { item : props.item };
	}

	render() {
		const item = this.state.item;
		return (
			<ListItem>
				<Checkbox checked={item.done} />
				<ListItemText>
					<InputBase
						inputProps={{ "aria-label" : "naked" }}
						type="text"
						id={item.id}
						name={item.id}
						value={item.title}
						multiline={true}
						fullWidth={true}
					/>
				</ListItemText>

				<ListItemSecondaryAction>
					<IconButton aria-label="Delete Todo">
						<DeleteOutlined />
					</IconButton>
				</ListItemSecondaryAction>

			</ListItem>
		);
	}
}

export default Todo;