import { Injectable } from '@nestjs/common';

@Injectable()
export class SongsService {
    //local db or array
    private readonly songs: any[] = []

    create(song){
        this.songs.push(song);
        return this.songs
    }

    findAll(){
        throw new Error('Error fetching data from DB')
        return this.songs;
    }

}
