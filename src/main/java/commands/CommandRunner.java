package commands;

import java.util.HashMap;
import java.util.Map;

import exceptions.DukeException;
import exceptions.StorageException;
import input.Input;
import task.TaskModel;


/**
 * Class to setup commands with their dependencies and expose method for interaction
 */
public class CommandRunner {
    /**
     * Stores mapping from command name (String) to corresponding Command object
     */
    private Map<String, Command> commandMap;

    /**
     * Creates new CommandRunner
     */
    public CommandRunner() throws StorageException {
        TaskModel taskModel = new TaskModel();

        // Add new commands here
        Command list = new ListCommand(taskModel);
        Command add = new TodoCommand(taskModel);
        Command mark = new MarkCommand(taskModel);
        Command unmark = new UnmarkCommand(taskModel);
        Command deadline = new DeadlineCommand(taskModel);
        Command event = new EventCommand(taskModel);
        Command delete = new DeleteCommand(taskModel);
        Command exit = new ExitCommand(taskModel);
        Command find = new FindCommand(taskModel);

        Command[] commands = new Command[] { list, add, mark, exit, unmark, deadline, event, delete, find };
        commandMap = new HashMap<>();

        for (int i = 0; i < commands.length; i++) {
            Command cmd = commands[i];
            commandMap.put(cmd.commandName, cmd);
        }
    }

    /**
     * Finds appropriate command to run for the input and returns response, or throws error if command not valid
     * @param input Input object
     * @return CommandResponse object representing response from this Command
     * @throws DukeException - if command name is unrecognised
     */
    public CommandResponse run(Input input) throws DukeException {
        String cmdInput = input.getCommandName();
        if (!commandMap.containsKey(cmdInput)) {
            throw new DukeException(String.format("Oh no! I do not recognise the command '%s'.", cmdInput));
        }

        return commandMap.get(cmdInput).run(input);
    }
}
