import { Controller, Delete, Post, Put, Get, Body, HttpException, HttpStatus, Param, ParseIntPipe} from '@nestjs/common';
import { SongsService } from './songs.service';
import { CreateSongDTO } from './dto/create-song-dto';

@Controller('songs')
export class SongsController {
    constructor(private songsService: SongsService){}

    @Post()
    create(@Body() createSongDTO: CreateSongDTO){
        return this.songsService.create(createSongDTO);
    }
    
    @Get()
    findAll(){
        try{
            return this.songsService.findAll();
        }catch(e){
            // throw new HttpException(
            //     'Server Error',
            //     HttpStatus.INTERNAL_SERVER_ERROR,
            //     {
            //         cause: e,
            //     },
            // );
            console.log('I am in catch block for this error:'+ e)
        }
        
        
    }

    @Get(':id')
    find(
        @Param(
        'id',
        new ParseIntPipe({errorHttpStatusCode:HttpStatus.NOT_ACCEPTABLE})
    ) id:number,
    ){
        return `Find song based on id ${typeof id}`;
    }

    @Put(':id')
    update(){
        return "Update song based on id";
    }

    @Delete(':id')
    delete(){
        return "Delete song based on id";
    }
    
}
